package com.geo.ablemove.presentation.registration

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.geo.ablemove.R
import com.geo.ablemove.ui.components.Background
import com.geo.ablemove.ui.components.buttons.SocialLoginButton
import com.geo.ablemove.ui.components.text.LoginTextField
import com.geo.ablemove.ui.theme.AbleMoveDimens


@Composable
fun RegistrationScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

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
                text = stringResource(R.string.registration_description),
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            Spacer(Modifier.height(28.dp))

            // Registration card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(36.dp))
                    .background(colorScheme.surface)
                    .padding(28.dp)
            ) {
                Text(
                    text = stringResource(R.string.registration_welcome),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.registration_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(30.dp))

                // Email
                Text(
                    text = stringResource(
                        R.string.registration_email_label
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorScheme.onSurface
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = email,
                    onValueChange = { email = it },
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
                Text(
                    text = stringResource(
                        R.string.registration_password_label
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorScheme.onSurface
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = password,
                    onValueChange = { password = it },
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
                Text(
                    text = stringResource(
                        R.string.registration_confirm_password_label
                    ),
                    style = MaterialTheme.typography.labelLarge,
                    color = colorScheme.onSurface
                )

                Spacer(Modifier.height(8.dp))

                LoginTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = stringResource(
                        R.string.registration_confirm_password_placeholder
                    ),
                    visualTransformation = if (confirmPasswordVisible) {
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
                                imageVector = if (confirmPasswordVisible) {
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

                // Create account button
                Button(
                    onClick = {
                        // onRegisterClick(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(
                            R.string.registration_sign_up
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.width(12.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
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
                    text = stringResource(
                        R.string.registration_google
                    ),
                    icon = R.drawable.ic_google,
                    onClick = {
                        // onGoogleSignUpClick()
                    }
                )

                Spacer(Modifier.height(12.dp))

                SocialLoginButton(
                    text = stringResource(
                        R.string.registration_facebook
                    ),
                    icon = R.drawable.ic_facebook,
                    onClick = {
                        // onFacebookSignUpClick()
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
                    onClick = {
                        // onSignInClick()
                    }
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
                onClick = {
                    // onGuestClick()
                }
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
