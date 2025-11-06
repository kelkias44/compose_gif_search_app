package com.example.gif_search_app.data.mapper

import com.example.gif_search_app.data.model.GifData
import com.example.gif_search_app.data.model.Image
import com.example.gif_search_app.data.model.Images
import org.junit.Assert.*
import org.junit.Test

class GifMapperTest {
    
    @Test
    fun `toDomain converts GifData to Gif correctly`() {
        val gifData = GifData(
            id = "test_gif",
            title = "Test GIF",
            url = "www.test.com",
            embedUrl = "test.com",
            type = "gif",
            rating = "3.0",
            images = Images(
                downsized = Image(
                    url = "https://example.com/fixed.gif",
                    width = "200",
                    height = "200"
                ),
                previewGif = Image(
                    url = "https://example.com/original.gif",
                    width = "400",
                    height = "300"
                )
            )
        )
        
        val result = gifData.toDomain()
        
        assertEquals("test_gif", result.id)
        assertEquals("Test GIF", result.title)
        assertEquals("https://example.com/fixed.gif", result.images.downsized.url)
        assertEquals("https://example.com/original.gif", result.images.previewGif.url)
        assertEquals("400", result.images.previewGif.width)
        assertEquals("300", result.images.previewGif.height)
    }
    

}


