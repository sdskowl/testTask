import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MainKtTest {

    @Test
    fun validator() {
        val invalidList = listOf(
            "bb 3-6: bhhkkbbjjjb",
            "",
            "b -1-6: bhhkkbbjjjb",
            "z a-4: asfalseiruqwo",
            "z 4-f: asfalseiruqwo",
            "b 3-6666666666666: bhhkkbbjjjb",
            "b 36666666666666-6: bhhkkbbjjjb",
            "z 2-4: asfalseiruqwo",
            "a 1-5: abaaaaaaaacdj",
            "z 2-4: ",
            "  1-5: abaaaa aaacdj",
            "  1-5: abaaaaaaaacdj",
            "bb 1-6: bhhkkbbjjjb",
        )
        invalidList.forEach { str ->
            assertEquals(false, str.validator().answer)
        }
        val validPasswordList = listOf("a 1-5: abcdj", "b 3-6: bhhkkbbjjjb", "b 6-1: bhhkkbbjjjb")
        validPasswordList.forEach { str ->
            assertEquals(true, str.validator().answer)
        }

    }
}