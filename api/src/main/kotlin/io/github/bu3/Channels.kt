package io.github.bu3

import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel

interface Channels {

    @Output("createEmployeeOutput")
    abstract fun createEmployeeOutput(): MessageChannel

    @Input("createEmployeeInput")
    abstract fun createEmployeeInput(): SubscribableChannel

    @Output("deleteEmployeeOutput")
    abstract fun deleteEmployeeOutput(): MessageChannel

    @Input("deleteEmployeeInput")
    abstract fun deleteEmployeeInput(): SubscribableChannel

    @Output("deleteAllEmployeesOutput")
    abstract fun deleteAllEmployeesOutput(): MessageChannel

    @Input("deleteAllEmployeesInput")
    abstract fun deleteAllEmployeesInput(): SubscribableChannel
}