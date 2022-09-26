package space.irsi7.cryptoviewer.model

data class TokenInfo (
    val id: String,
    val name: String,
    val image: Map<String,String>,
    val description: Map<String,String>,
    val categories: ArrayList<String>,
)