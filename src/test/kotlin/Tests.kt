import data.Priority
import data.Task
import data.TasksRepositoryMemory
import org.junit.jupiter.api.DisplayName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


@DisplayName("Тесты для проверки основных функций консольного приложения ToDo List")
class Tests {
    lateinit var tasks: TasksRepositoryMemory
    private val testTask = Task(1, "TestTask", Priority.HIGH)
    private val listOfTasks = mutableListOf(
        Task(1, "TaskC", Priority.LOW),
        Task(2, "TaskB", Priority.HIGH),
        Task(3, "TaskA", Priority.MEDIUM)
    )

    @BeforeTest
    fun before() {
        tasks = TasksRepositoryMemory()
    }

    @Test
    @DisplayName("Тест добавления задачи и появления ее в списке")
    fun testAddTask() {
        tasks.addTask(testTask)
        assertEquals("TestTask", tasks.getTasks().first().name)
    }

    @Test
    @DisplayName("Тест завершения задачи")
    fun testCompleteTask() {
        val taskId = tasks.addTask(testTask)
        tasks.completeTask(taskId)
        assertEquals(true, tasks.getTasks().first { it.id == taskId }.completed)
    }

    @Test
    @DisplayName("Тест корректности работы фильтра по незавершенным задачам")
    fun testFilterNonCompletedTasks() {
        listOfTasks.forEach {
            val taskId = tasks.addTask(it)
            tasks.completeTask(taskId)
        }
        assertEquals(true, tasks.getTasks(completed = false).isEmpty())
    }

    @Test
    @DisplayName("Тест корректности работы фильтра по завершенным задачам")  //если задание предполагает, что функция getTasks(completed = true) должна возвращать только завершенные задачи
    fun testFilterCompletedTasks() {
        listOfTasks.last().completed = true
        listOfTasks.forEach { tasks.addTask(it) }

        val listTaskCompletedSize = listOfTasks.filter { it.completed }.size
        val taskCompletedSize = tasks.getTasks(completed = true).size

        assertEquals(listTaskCompletedSize, taskCompletedSize)
    }

    @Test
    @DisplayName("Тест сортировки задач по названию")
    fun testSortTasksByName() {
        listOfTasks.forEach { tasks.addTask(it) }
        listOfTasks.sortBy { it.name }
        assertEquals(listOfTasks, tasks.getTasks())
    }

    @Test
    @DisplayName("Тест сортировки задач по сроку исполнения")
    fun testSortTasksByPriority() {
        listOfTasks.forEach { tasks.addTask(it) }
        listOfTasks.sortByDescending { it.priority }
        assertEquals(listOfTasks, tasks.getTasks())
    }
}