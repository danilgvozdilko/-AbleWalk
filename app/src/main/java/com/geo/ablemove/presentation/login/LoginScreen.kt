package com.geo.ablemove.presentation.login

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onCreateAccountClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val activity = LocalActivity.current as ComponentActivity
    val scope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showForgotPasswordDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var resetEmail by rememberSaveable {
        mutableStateOf("")
    }

    val webClientId = stringResource(R.string.google_web_client_id)

    val googleSignInManager = remember {
        GoogleSignInManager()
    }

    val facebookSignInManager = remember {
        FacebookSignInManager()
    }

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) onLoginSuccess()
    }

    LoginScreen(
        isLoading = uiState.isLoading,
        emailError = uiState.emailError,
        passwordError = uiState.passwordError,
        loginError = uiState.loginError,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onForgotPasswordClick = { email ->
            if (!uiState.isLoading) {
                resetEmail = email
                viewModel.openPasswordReset()
                showForgotPasswordDialog = true
            }
        },
        onCreateAccountClick = onCreateAccountClick,
        onGuestClick = viewModel::signInGuest,
        onLoginClick = viewModel::signInWithEmail,
        onGoogleClick = {
            if (viewModel.tryStartSocialSignIn()) {
                scope.launch {
                    try {
                        val token = googleSignInManager.signIn(activity, webClientId)
                        viewModel.signInWithGoogle(token)
                    } catch (e: GetCredentialCancellationException) {
                        viewModel.onSocialSignInCancelled()
                    } catch (e: CancellationException) {
                        viewModel.onSocialSignInCancelled()
                        throw e
                    } catch (e: Exception) {
                        viewModel.onSocialSignInError("Google", e)
                    }
                }
            }
        },
        onFacebookClick = {
            if (viewModel.tryStartSocialSignIn()) {
                scope.launch {
                    try {
                        val token = facebookSignInManager.signIn(activity)
                        viewModel.signInWithFacebook(token)
                    } catch (e: FacebookSignInCancelledException) {
                        viewModel.onSocialSignInCancelled()
                    } catch (e: CancellationException) {
                        viewModel.onSocialSignInCancelled()
                        throw e
                    } catch (e: Exception) {
                        viewModel.onSocialSignInError("Facebook", e)
                    }
                }
            }
        }
    )

    if (showForgotPasswordDialog) {
        ForgotPasswordDialog(
            initialEmail = resetEmail,
            state = uiState.passwordReset,
            onDismiss = {
                showForgotPasswordDialog = false
                viewModel.dismissPasswordReset()
            },
            onSendClick = viewModel::sendPasswordResetEmail
        )
    }
}

@Composable
fun LoginScreen(
    isLoading: Boolean = false,
    emailError: String? = null,
    passwordError: String? = null,
    loginError: String? = null,
    onEmailChanged: () -> Unit = {},
    onPasswordChanged: () -> Unit = {},
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onForgotPasswordClick: (String) -> Unit = {},
    onCreateAccountClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    onGuestClick: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val colorScheme = MaterialTheme.colorScheme

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
                    contentDescription = stringResource(R.string.login_app_logo),
                    modifier = Modifier.size(30.dp),
                    tint = colorScheme.primary
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.login_description),
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            Spacer(Modifier.height(28.dp))

            // Login card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(36.dp))
                    .background(colorScheme.surface)
                    .padding(28.dp)
            ) {
                Text(
                    text = stringResource(R.string.login_welcome_back),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.login_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(30.dp))

                Text(
                    text = stringResource(R.string.login_email_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorScheme.onSurface
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = email,
                    onValueChange = { email = it; onEmailChanged() },
                    placeholder = stringResource(
                        R.string.login_email_placeholder
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

                if (emailError != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(emailError, color = colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }

                Spacer(Modifier.height(22.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.login_password_label),
                        style = MaterialTheme.typography.labelLarge,
                        color = colorScheme.onSurface
                    )

                    TextButton(
                        onClick = { onForgotPasswordClick(email) },
                        contentPadding = PaddingValues(0.dp),
                        enabled = !isLoading
                    ) {
                        Text(
                            text = stringResource(R.string.login_forgot_password),
                            color = colorScheme.primary
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                LoginTextField(
                    value = password,
                    onValueChange = { password = it; onPasswordChanged() },
                    placeholder = stringResource(
                        R.string.login_password_placeholder
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
                                    R.string.login_password_visibility
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

                if (passwordError != null) {
                    Spacer(Modifier.height(6.dp))
                    Text(passwordError, color = colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }

                if (loginError != null) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        loginError,
                        color = colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        onLoginClick(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.dp,
                            color = colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.login_sign_in),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

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
                            R.string.login_or_continue_with
                        ),
                        modifier = Modifier.padding(horizontal = 12.dp),
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
                    text = stringResource(R.string.login_google),
                    icon = R.drawable.ic_google,
                    onClick = onGoogleClick,
                    enabled = !isLoading
                )

                Spacer(Modifier.height(12.dp))

                SocialLoginButton(
                    text = stringResource(R.string.login_facebook),
                    icon = R.drawable.ic_facebook,
                    onClick = onFacebookClick,
                    enabled = !isLoading
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.login_no_account),
                    color = colorScheme.onSurfaceVariant
                )

                TextButton(onClick = onCreateAccountClick, enabled = !isLoading) {
                    Text(
                        text = stringResource(R.string.login_sign_up),
                        color = colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            TextButton(onClick = onGuestClick, enabled = !isLoading) {
                Text(
                    text = stringResource(R.string.login_explore_guest),
                    color = colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
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
fun ForgotPasswordDialog(
    initialEmail: String,
    state: PasswordResetState,
    onDismiss: () -> Unit,
    onSendClick: (String) -> Unit
) {
    var email by rememberSaveable(initialEmail) { mutableStateOf(initialEmail) }
    val isLoading = state is PasswordResetState.Loading
    val isSuccess = state is PasswordResetState.Sent
    val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = { Text("Reset password") },
        text = {
            Column {
                if (isSuccess) {
                    Text("If an account exists for this email, you'll receive a password reset link. Check your inbox and spam folder.")
                } else {
                    Text("Enter your email address to receive a password reset link.")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        enabled = !isLoading,
                        isError = state is PasswordResetState.Error,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (state is PasswordResetState.Error) {
                        Spacer(Modifier.height(8.dp))
                        Text(state.message, color = MaterialTheme.colorScheme.error)
                    }
                    if (isLoading) {
                        Spacer(Modifier.height(12.dp))
                        CircularProgressIndicator(modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (isSuccess) onDismiss() else onSendClick(email.trim()) },
                enabled = isSuccess || (!isLoading && isValidEmail)
            ) { Text(if (isSuccess) "Done" else "Send link") }
        },
        dismissButton = {
            if (!isSuccess) {
                TextButton(onClick = onDismiss, enabled = !isLoading) { Text("Cancel") }
            }
        }
    )
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}