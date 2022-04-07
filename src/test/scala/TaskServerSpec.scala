import com.twitter.inject.server.FeatureTest
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.finagle.http.Status

class TaskServerSpec extends FeatureTest {

  override val server: EmbeddedHttpServer = new EmbeddedHttpServer(
    twitterServer = new TaskServer
  )

  test("Ping should return pong") {
    server.httpGet(path = "/ping", andExpect = Status.Ok)
  }

  test("Create new todo should return status created with id equal to 0") {
    server.httpPost(
      path = "/todo",
      postBody = """
        |{
        | "id": -1,
        | "detail": "buy banana"
        |}
        """.stripMargin,
      andExpect = Status.Created,
      withJsonBody = "created 0"
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
