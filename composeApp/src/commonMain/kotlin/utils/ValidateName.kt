package utils

class ValidateName {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "Name can't be blank!"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}