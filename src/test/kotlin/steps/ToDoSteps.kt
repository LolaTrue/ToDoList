package steps

import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.cucumber.java8.En
import kotlin.test.assertEquals

class ToDoSteps : En {

    init {
        lateinit var tasks: TasksRepositoryMemory
        val testTask = Task(1, "TestTask", Priority.HIGH)
        val listOfTasks = mutableListOf(
            Task(1, "TaskC", Priority.LOW),
            Task(2, "TaskB", Priority.HIGH),
            Task(3, "TaskA", Priority.MEDIUM)
        )

        Given("Tasks repository memory") {
            tasks = TasksRepositoryMemory()
        }

        When("Add task") {
            tasks.addTask(testTask)
        }

        When("Complete task") {
            tasks.completeTask(tasks.getTasks().first().id!!)
        }

        When("Add list of tasks") {
            listOfTasks.forEach { tasks.addTask(it) }
        }

        When("Complete tasks") {
            listOfTasks.forEach {
                tasks.completeTask(it.id!!)
            }
        }

        When("Sort tasks by name") {
            listOfTasks.sortBy { it.name }
        }

        Then("Task should be in list") {
            assertEquals("TestTask", tasks.getTasks().first().name)
        }

        Then("Task should be completed") {
            assertEquals(true, tasks.getTasks().first().completed)
        }

        Then("List of uncompleted tasks should be empty") {
            assertEquals(true, tasks.getTasks(completed = false).isEmpty())
        }

        Then("List of tasks should be sorted") {
            assertEquals(listOfTasks, tasks.getTasks())
        }

    }
}