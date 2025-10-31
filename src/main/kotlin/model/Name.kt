package model

@JvmInline
value class Name(val name: String) { // Como só tenho um construtor, pode ser uma value class
    init {
        require(isValid(name)) {"Invalid name $name"}
    }

    override fun toString() = name

    // Chamar isValid name antes de sequer criar o objeto name:
    companion object {
        fun isValid(name: String) = name.isNotBlank() && name.all { it.isLetterOrDigit() }
    }
}