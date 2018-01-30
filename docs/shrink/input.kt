package demo.Shrink
//HISTORY 100(Mr. Xcoder)[59487]{Replaced `i%it==0` with `i%it<1`}
//STARTSUB
fun f(i:Int):Int{
    return i.toString()
        .map { it.toInt()-48 }
        .filter { it >1 && i % it < 1}
        .map { f(i/it) }
        .min() ?: i
}
//ENDSUB

val tests = listOf(
        1 to 1,
        7 to 1,
        10 to 10,
        24 to 1,
        230 to 23,
        234 to 78,
        10800 to 1,
        10801 to 10801,
        50976 to 118,
        129500 to 37,
        129528 to 257,
        8377128 to 38783,
        655294464 to 1111)

fun main(args: Array<String>) {
    for ( test in tests) {
        val computed = f(test.first)
        val expected = test.second
        if (computed != expected) {
            throw AssertionError("$computed != $expected")
        }
    }
}