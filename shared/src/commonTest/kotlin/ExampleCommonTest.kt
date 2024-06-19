import kotlin.test.Test
import kotlin.test.assertEquals

class ExampleCommonTest {

    @Test
    fun shouldProduce2() {
        // Arrange
        val expected = 2

        // Act
        val actual = 1 + 1

        //Assert
        assertEquals(expected, actual)
    }

}