def deploylist = "difflog.txt"

def showGitDiff(String HEAD_SHA, String BASE_SHA) {
  /*execute git diff command to retrieve diff between input commits */
  /*def DIFFLOG = sh(script: "git diff --name-only --diff-filter=ACMR ${HEAD_SHA}...${BASE_SHA}", returnStdout: true) */

  // create difflog.txt file
  sh(script: "git diff --name-only --diff-filter=ACMR ${HEAD_SHA}...${BASE_SHA} > difflog.txt", returnStdout: true)
}

def rpdDiff() {
  def RPD_BUILD = false
  if (sh(script: "grep -e rpd difflog.txt", returnStatus: true) == "0") {
    RPD_BUILD = true
  }
  return RPD_BUILD
}

def webDiff() {
  def WEB_BUILD = false
  if (sh(script: "grep -e WEB/ difflog.txt", returnStatus: true) == "0") {
    WEB_BUILD = true
  }
  return WEB_BUILD
}

def dbDiff() {
  def DB_BUILD = false
  if (sh(script: "grep -e DB/ difflog.txt", returnStatus: true) == "0") {
    DB_BUILD = true
  }
  return DB_BUILD
}

def shDiff() {
  def SHELL_BUILD = false
  if (sh(script: "grep -e SHELL/ difflog.txt", returnStatus: true) == "0") {
    SHELL_BUILD = true
  }
  return SHELL_BUILD
}
return this
