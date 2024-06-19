import kotlin.test.Test
import kotlin.test.assertTrue

class LitmusTest {

    @Test
    fun shouldDisplayPrettyString() {
        val platform = getPlatform().name

        //Assert
        assertTrue(platform.isNotBlank())
    }

}