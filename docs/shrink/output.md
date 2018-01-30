# [Kotlin](https://kotlinlang.org), <s>100</s> 99 bytes

<!-- language: lang-kotlin -->

    fun f(i:Int):Int{return i.toString().map{it.toInt()-48}.filter{it>1&&i%it<1}.map{f(i/it)}.min()?:i}
## Beautified

<!-- language: lang-kotlin -->

    fun f(i:Int):Int{
        return i.toString()
            .map { it.toInt()-48 }
            .filter { it >1 && i % it < 1}
            .map { f(i/it) }
            .min() ?: i
    }
## Test

<!-- language: lang-kotlin -->


    fun f(i:Int):Int{return i.toString().map{it.toInt()-48}.filter{it>1&&i%it<1}.map{f(i/it)}.min()?:i}
    
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
## TIO
[TryItOnline](https://tio.run/##ZZHLbsIwEEX3+QoXUWRLLc2TGARULLroqot+QQQOHTXYyB76EMq3p44NIdBZWJmbMzeT60+FFcimKQ+SlBRmrxJZexy1wIOWBMao3lGD3FI23hX7I6BVLEDZY8rrcQkVCm3VZTQawT3gPKodZ82eAJltQFL2PIO6Cb6KiqAwaMiCVGDwraQBOVVEUJHooevzmz4KnRBelDi9QeLEMXHSlxyU874RD8N/5jz0C7QPFz0Lp/nE6VHfIZ5m3iLJr9WYuwWynsyTPI+8nvCc93abZFk8TdOJ/w1bLAjaa9gVNrFCb82MrLQufuc+/yUjRzdbKk2oy5GA9HmeX7XVhrxWu/0BxcbmXNKWsPekDbIrSPzsxdpDDjFireSmQ6AktPO5W3R4/1tt4YdW32RljNAISr5orTQdDPujw/Ps4LJBHfizbv4A)
## Edits
- -1 [Mr. Xcoder](https://codegolf.stackexchange.com/users/59487) - Replaced `i%it==0` with `i%it<1`
