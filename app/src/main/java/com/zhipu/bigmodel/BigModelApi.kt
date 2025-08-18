package com.zhipu.bigmodel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BigModelApi {
    
    @POST("api/paas/v4/chat/completions")
    fun generateText(
        @Header("Authorization") authToken: String,
        @Body request: ChatCompletionRequest
    ): Call<ChatCompletionResponse>
}

data class ChatCompletionRequest(
    val model: String = "glm-4",  // 使用GLM-4模型
    val messages: List<Message>,
    val temperature: Float = 0.7f,
    val max_tokens: Int = 1024
)

data class Message(
    val role: String,
    val content: String
)

data class ChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val index: Int,
    val message: Message,
    val finish_reason: String
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)