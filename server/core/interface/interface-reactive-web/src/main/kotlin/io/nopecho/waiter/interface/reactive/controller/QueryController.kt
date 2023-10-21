package io.nopecho.waiter.`interface`.reactive.controller

import io.nopecho.waiter.application.handlers.AsyncQueryHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class QueryController(
    private val queryHandler: AsyncQueryHandler
)