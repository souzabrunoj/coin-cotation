package br.com.souzabrunoj.coinquotation.utils

enum class CoinName(val symbol: String, val abbreviation: String) {
    DOLLAR("$", "usd"),
    REAL("R$", "brl"),
    EURO("Є", "eur"),
}