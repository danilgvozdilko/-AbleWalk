package com.geo.ablemove.presentation.registration

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.geo.ablemove.R
import com.geo.ablemove.data.helpers.FacebookSignInCancelledException
import com.geo.ablemove.data.helpers.FacebookSignInManager
import com.geo.ablemove.data.helpers.GoogleSignInManager
import com.geo.ablemove.ui.components.background.Background
import com.geo.ablemove.ui.components.buttons.SocialLoginButton
import com.geo.ablemove.ui.components.text.LoginTextField
import com.geo.ablemove.ui.theme.AbleMoveDimens
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun RegistrationRoute(
    onRegistrationSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val activity = LocalActivity.current as ComponentActivity
    val scope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val webClientId = stringResource(
        R.string.google_web_client_id
    )

    val googleSignInManager = remember {
        GoogleSignInManager()
    }

    val facebookSignInManager = remember {
        FacebookSignInManager()
    }

    LaunchedEffect(uiState) {
        if (uiState is RegistrationUiState.Success) {
            onRegistrationSuccess()
        }
    }

    RegistrationScreen(
        uiState = uiState,

        onRegisterClick = viewModel::registerWithEmail,

        onGoogleClick = {
            if (uiState !is RegistrationUiState.Loading) {
                viewModel.onSocialSignInStarted()

                scope.launch {
                    try {
                        val idToken = googleSignInManager.signIn(
                            activity = activity,
                            webClientId = webClientId
                        )

                        viewModel.signInWithGoogle(idToken)

                    } catch (e: GetCredentialCancellationException) {
                        viewModel.onSocialSignInCancelled()

                    } catch (e: CancellationException) {
                        viewModel.onSocialSignInCancelled()
                        throw e

                    } catch (e: Exception) {
                        Log.e(
                            "RegistrationAuth",
                            "Google sign-in failed",
                            e
                        )

                        viewModel.onSocialSignInError(e)
                    }
                }
            }
        },

        onFacebookClick = {
            if (uiState !is RegistrationUiState.Loading) {
                viewModel.onSocialSignInStarted()

                scope.launch {
                    try {
                        Log.d(
                            "RegistrationAuth",
                            "Starting Facebook SDK"
                        )

                        val accessToken =
                            facebookSignInManager.signIn(activity)

                        viewModel.signInWithFacebook(accessToken)

                    } catch (e: FacebookSignInCancelledException) {
                        viewModel.onSocialSignInCancelled()

                    } catch (e: CancellationException) {
                        viewModel.onSocialSignInCancelled()
                        throw e

                    } catch (e: Exception) {
                        Log.e(
                            "RegistrationAuth",
                            "Facebook sign-in failed",
                            e
                        )

                        viewModel.onSocialSignInError(e)
                    }
                }
            }
        },

        onGuestClick = viewModel::signInAsGuest,

        onSignInClick = onNavigateToLogin,

        onClearError = viewModel::clearError
    )
}

@Composable
fun RegistrationScreen(
    uiState: RegistrationUiState,
    onRegisterClick: (
        nickname: String,
        email: String,
        password: String,
        confirmPassword: String
    ) -> Unit,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onGuestClick: () -> Unit,
    onSignInClick: () -> Unit,
    onClearError: () -> Unit
) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var confirmPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val colorScheme = MaterialTheme.colorScheme
    val isLoading = uiState is RegistrationUiState.Loading
    val error = (uiState as? RegistrationUiState.Error)?.message

    Background {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = AbleMoveDimens.screenHorizontalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        colorScheme.surface.copy(alpha = 0.85f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = stringResource(
                        R.string.registration_app_logo
                    ),
                    modifier = Modifier.size(30.dp),
                    tint = colorScheme.primary
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.registration_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(
                    R.string.registration_description
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            Spacer(Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(36.dp))
                    .background(colorScheme.surface)
                    .padding(28.dp)
            ) {
                Text(
                    text = stringResource(
                        R.string.registration_welcome
                    ),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = stringResource(
                        R.string.registration_subtitle
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(30.dp))

                // Nickname
                RegistrationFieldLabel(
                    text = stringResource(
                        R.string.registration_nickname_label
                    )
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = nickname,
                    onValueChange = {
                        nickname = it
                        onClearError()
                    },
                    placeholder = stringResource(
                        R.string.registration_nickname_placeholder
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(Modifier.height(22.dp))

                // Email
                RegistrationFieldLabel(
                    text = stringResource(
                        R.string.registration_email_label
                    )
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        onClearError()
                    },
                    placeholder = stringResource(
                        R.string.registration_email_placeholder
                    ),
                    trailingIcon = {
                        Text(
                            text = "@",
                            fontSize = 22.sp,
                            color = colorScheme.onSurfaceVariant
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(Modifier.height(22.dp))

                // Password
                RegistrationFieldLabel(
                    text = stringResource(
                        R.string.registration_password_label
                    )
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        onClearError()
                    },
                    placeholder = stringResource(
                        R.string.registration_password_placeholder
                    ),
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = stringResource(
                                    R.string.registration_password_visibility
                                ),
                                tint = colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(Modifier.height(22.dp))

                // Confirm password
                RegistrationFieldLabel(
                    text = stringResource(
                        R.string.registration_confirm_password_label
                    )
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        onClearError()
                    },
                    placeholder = stringResource(
                        R.string.registration_confirm_password_placeholder
                    ),
                    visualTransformation =
                        if (confirmPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                confirmPasswordVisible =
                                    !confirmPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (confirmPasswordVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                contentDescription = stringResource(
                                    R.string.registration_password_visibility
                                ),
                                tint = colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(Modifier.height(24.dp))

                // Validation error
                if (error != null) {
                    Text(
                        text = error,
                        color = colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(Modifier.height(12.dp))
                }

                // Create account
                Button(
                    onClick = {
                        onRegisterClick(
                            nickname,
                            email,
                            password,
                            confirmPassword
                        )
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(
                                R.string.registration_sign_up
                            ),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(Modifier.width(12.dp))

                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Social registration divider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = colorScheme.outlineVariant
                    )

                    Text(
                        text = stringResource(
                            R.string.registration_or_continue_with
                        ),
                        modifier = Modifier.padding(
                            horizontal = 12.dp
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = colorScheme.outlineVariant
                    )
                }

                Spacer(Modifier.height(16.dp))

                SocialLoginButton(
                    text = stringResource(
                        R.string.registration_google
                    ),
                    icon = R.drawable.ic_google,
                    onClick = {
                        if (!isLoading) onGoogleClick()
                    }
                )

                Spacer(Modifier.height(12.dp))

                SocialLoginButton(
                    text = stringResource(
                        R.string.registration_facebook
                    ),
                    icon = R.drawable.ic_facebook,
                    onClick = {
                        if (!isLoading) onFacebookClick()
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            // Navigate to login
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.registration_have_account
                    ),
                    color = colorScheme.onSurfaceVariant
                )

                TextButton(
                    onClick = onSignInClick,
                    enabled = !isLoading
                ) {
                    Text(
                        text = stringResource(
                            R.string.registration_sign_in
                        ),
                        color = colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Guest mode
            TextButton(
                onClick = onGuestClick,
                enabled = !isLoading
            ) {
                Text(
                    text = stringResource(
                        R.string.registration_explore_guest
                    ),
                    color = colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.width(8.dp))

                Icon(
                    imageVector =
                        Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun RegistrationFieldLabel(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}
