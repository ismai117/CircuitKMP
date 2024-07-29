package utils

interface Validation {
    val validateUsername: ValidateUsername
    val validateName: ValidateName
    val validateEmail: ValidateEmail
    val validatePassword: ValidatePassword
    val validateConfirmPassword: ValidateConfirmPassword
}

class ValidationImpl : Validation {

    override val validateUsername: ValidateUsername by lazy{
        ValidateUsername()
    }

    override val validateName: ValidateName by lazy {
        ValidateName()
    }

    override val validateEmail: ValidateEmail by lazy {
        ValidateEmail()
    }
    override val validatePassword: ValidatePassword by lazy {
        ValidatePassword()
    }

    override val validateConfirmPassword: ValidateConfirmPassword by lazy {
        ValidateConfirmPassword()
    }

}