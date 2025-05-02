package com.example.myapplication.models

import com.example.myapplication.interfaces.ApiService
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        apiService = mockk()
        repository = UserRepository(apiService)
    }

    @Test
    fun `fetchUsers returns user list on success`() {
        // Створюємо фейковий виклик
        val mockCall = mockk<Call<List<User>>>(relaxed = true)
        val mockUser = User("Іван", "Іванов", null, Roles.USER, null, null, null, 5)
        val response = Response.success(listOf(mockUser))

        // Коли викликається getUsers(), повертається mockCall
        every { apiService.getUsers() } returns mockCall

        // Коли викликається enqueue, викликаємо onResponse з успішною відповіддю
        every { mockCall.enqueue(any()) } answers {
            val callback = firstArg<Callback<List<User>>>()
            callback.onResponse(mockCall, response)
        }

        var users: List<User>? = null
        var error: String? = "старт"

        repository.fetchUsers { u, e ->
            users = u
            error = e
        }

        assertEquals(1, users!!.size)
        assertEquals("Іван", users!![0].firstName)
        assertNull(error)
    }
}