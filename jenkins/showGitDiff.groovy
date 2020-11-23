def showGitDiff(String HEAD_SHA, String BASE_SHA) {
  /*execute git diff command to retrieve diff between input commits */
  /*def DIFFLOG = sh(script: "git diff --name-only --diff-filter=ACMR ${HEAD_SHA}...${BASE_SHA}", returnStdout: true) */
  def RPD_BUILD = false
  def WEB_BUILD = false
  def DB_BUILD = false
  def SHELL_BUILD = false
  def deploylist = "difflog.txt"

  // create difflog.txt file
  sh(script: "git diff --name-only --diff-filter=ACMR ${HEAD_SHA}...${BASE_SHA} > ${deploylist}", returnStdout: true)

  // identify deployment type based on extracted diff
  if ((sh(script: "grep -e liverpd ${deploylist}"), returnStatus: true) == "0") {
    RPD_BUILD = true
  }
  else if ((sh(script: "grep -e WEB/ ${deploylist}"), returnStatus: true) == "0") {
    WEB_BUILD = true
  }
  else if ((sh(script: "grep -e DB/ ${deploylist}"), returnStatus: true) == "0") {
    DB_BUILD = true
  }
  else if ((sh(script: "grep -e SHELL/ ${deploylist}"), returnStatus: true) == "0") {
    SHELL_BUILD = true
  }
}
return this
