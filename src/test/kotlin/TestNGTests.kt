import data.Priority
import data.Task
import data.TasksRepositoryMemory
import org.testng.Assert.assertEquals
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class TestNGTests {
    lateinit var tasks: TasksRepositoryMemory
    private val testTask = Task(1, "TestTask", Priority.HIGH)
    private val listOfTasks = mutableListOf(
        Task(1, "TaskC", Priority.LOW),
        Task(2, "TaskB", Priority.HIGH),
        Task(3, "TaskA", Priority.MEDIUM)
    )

    @BeforeMethod
    fun setUp() {
        tasks = TasksRepositoryMemory()
    }

    @Test(description = "Тест добавления задачи и появления ее в списке")
    fun testAddTask() {
        tasks.addTask(testTask)
        assertEquals("TestTask", tasks.getTasks().first().name)
    }

    @Test(description = "Тест завершения задачи")
    fun testCompleteTask() {
        val taskId = tasks.addTask(testTask)
        tasks.completeTask(taskId)
        assertEquals(true, tasks.getTasks().first { it.id == taskId }.completed)
    }

    @Test(description = "Тест корректности работы фильтра по незавершенным задачам")
    fun testFilterUncompletedTasks() {
        listOfTasks.forEach {
            val taskId = tasks.addTask(it)
            tasks.completeTask(taskId)
        }
        assertEquals(true, tasks.getTasks(completed = false).isEmpty())
    }

    @Test(description = "Тест сортировки задач по названию")
    fun testSortTasksByName() {
        listOfTasks.forEach { tasks.addTask(it) }
        listOfTasks.sortBy { it.name }
        assertEquals(listOfTasks, tasks.getTasks())
    }

}