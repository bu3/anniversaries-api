package io.github.bu3.employees

class EmployeeCreatedEvent(var aggregate: Aggregate)
class EmployeeDeletedEvent(var aggregate: Aggregate)
class AllEmployeeDeletedEvent