def showGitDiff(String HEAD_SHA, String BASE_SHA) {
  /*execute git diff command to retrieve diff between input commits */
  /*def DIFFLOG = sh(script: "git diff --name-only --diff-filter=ACMR ${HEAD_SHA}...${BASE_SHA}", returnStdout: true) */
  // create difflog.txt file
  return ((sh(script: "git diff --name-only --diff-filter=ACMR ${HEAD_SHA}...${BASE_SHA} > difflog.txt", returnStatus: true)) == 0) ? true : false
}


def deployInit(String type) {
  def difflog = "difflog.txt"
  if (type == 'RPD') {
    return (sh(script: "grep -e rpd ${difflog}", returnStatus: true) == 0) ? true : false
  }
  else if (type == 'WEB') {
    return (sh(script: "grep -e rpd ${difflog}", returnStatus: true) == 0) ? true : false
  }
  else if (type == 'DB') {
    return (sh(script: "grep -e DB/ ${difflog}", returnStatus: true) == 0) ? true : false
  }
  else if (type == 'SHELL') {
    return (sh(script: "grep -e SHELL/ ${difflog}", returnStatus: true) == 0) ? true : false
  }
}
return this
