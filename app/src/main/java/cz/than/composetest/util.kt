package cz.than.composetest


fun <A, B, C, D, R> (suspend (A, B, C, D) -> R).partially4(d: D): suspend (A, B, C) -> R =
    { a, b, c -> invoke(a, b, c, d) }