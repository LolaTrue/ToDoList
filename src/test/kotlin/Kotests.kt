import data.Priority
import data.Task
import data.TasksRepositoryMemory
import io.kotest.core.annotation.AutoScan
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlin.test.assertEquals

class Kotests : FunSpec({

    context("Тесты для проверки основных функций консольного приложения ToDo List") {
        lateinit var tasks: TasksRepositoryMemory
        val testTask = Task(1, "TestTask", Priority.HIGH)
        val listOfTasks = mutableListOf(
            Task(1, "TaskC", Priority.LOW),
            Task(2, "TaskB", Priority.HIGH),
            Task(3, "TaskA", Priority.MEDIUM)
        )

        beforeTest {
            tasks = TasksRepositoryMemory()
        }
        test("Тест добавления задачи и появления ее в списке") {
            tasks.addTask(testTask)
            assertEquals("TestTask", tasks.getTasks().first().name)
        }
        test("Тест сортировки задач по названию") {
            listOfTasks.forEach { tasks.addTask(it) }
            listOfTasks.sortBy { it.name }
            assertEquals(listOfTasks, tasks.getTasks())
        }
        test("Тест сортировки задач по сроку исполнения") {
            listOfTasks.forEach { tasks.addTask(it) }
            listOfTasks.sortByDescending { it.priority }
            assertEquals(listOfTasks, tasks.getTasks())
        }
        test("Тест завершения задачи") {
            val taskId = tasks.addTask(testTask)
            tasks.completeTask(taskId)
            assertEquals(true, tasks.getTasks().first { it.id == taskId }.completed)
        }
        test("Тест для результата теста Error") {
            throw MyEx()
        }
    }
})

@AutoScan
class RepeatOnFailureExtension : TestCaseExtension {

    private val maxRetryCount = 5

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        var result = execute(testCase)

        repeat(maxRetryCount) {
            if (result.isErrorOrFailure) {
                result = execute(testCase)
            } else return@repeat
        }
        return result
    }
}

class MyEx : Exception() {
    override val message: String
        get() = "Ошибонька"
}
