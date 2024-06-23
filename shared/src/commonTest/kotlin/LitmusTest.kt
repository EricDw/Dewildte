import kotlin.test.Test
import kotlin.test.assertTrue

class LitmusTest {

    @Test
    fun shouldDisplayPrettyString() {
        val platform = getPlatform().name

        // Act
        println(platform)

        //Assert
        assertTrue(platform.isNotBlank())
    }

}