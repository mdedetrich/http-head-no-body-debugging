object Token {
  private def emptyStringToNone(string: String): Option[String] =
    if (string.trim.isEmpty)
      None
    else
      Some(string)

  private def getDataFromEnv(id: String): String =
    sys.props
      .get(id)
      .flatMap(emptyStringToNone)
      .orElse(sys.env.get(id).flatMap(emptyStringToNone))
      .getOrElse("")

  lazy val getToken: String = getDataFromEnv("TOKEN")
}
