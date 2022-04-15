import com.twitter.inject.server.FeatureTest
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finagle.http.Status
import task._

class TaskServerSpec extends FeatureTest {
  private val stubIdService = new StubIdService()
  override val server: EmbeddedHttpServer = new EmbeddedHttpServer(
    twitterServer = new TaskServer
  ).bind[IdService].toInstance(stubIdService)

  test("Ping should return pong") {
    server.httpGet(path = "/ping", andExpect = Status.Ok)
  }

  test("Create new todo should return status created with id equal to 0") {
    server.httpPost(
      path = "/todo",
      postBody = """
        |{
        | "detail": "buy banana"
        |}
        """.stripMargin,
      andExpect = Status.Created,
      withJsonBody = "new todo was created"
    )
  }

  test(
    "Move item to doing should return status created and json body is 'Item was moved to doing"
  ) {
    server.httpPost(
      path = "/todo/next",
      postBody = """
      |{
      | "id": 0,
      | "detail": "buy banana"
      |}
      """.stripMargin,
      andExpect = Status.Created,
      withJsonBody = "Item was moved to doing"
    )
  }
  test("After move item to doing todo list should be empty") {
    server.httpGet(path = "/todo", withJsonBody = "[]")
  }

  test("After move item to doing doing list should contain 1 item") {
    server.httpGet(
      path = "/doing",
      withJsonBody = """[{"id":0, "detail":"buy banana"}]"""
    )
  }
}
