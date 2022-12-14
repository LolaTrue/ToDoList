Feature: ToDo list

  Scenario: Add task to list
    Given Tasks repository memory
    When Add task
    Then Task should be in list

  Scenario: Complete task
    Given Tasks repository memory
    When Add task
    When Complete task
    Then Task should be completed

  Scenario: Filter uncompleted tasks
    Given Tasks repository memory
    When Add list of tasks
    When Complete tasks
    Then List of uncompleted tasks should be empty

  Scenario: Sort tasks by name
    Given Tasks repository memory
    When Add list of tasks
    When Sort tasks by name
    Then List of tasks should be sorted