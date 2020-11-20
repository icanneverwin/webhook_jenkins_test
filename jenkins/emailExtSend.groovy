def emailExtSend(String type,
                 String pr_num,
                 String deploy_env,
                 String pr_title,
                 String head_branch,
                 String base_branch,
                 String pr_user,
                 String build_number,
                 String hostname,
                 String ip_addr,
                 List email_addrs) {
    if (type == "Start") {
        emailext (
            subject: "Starting deployment of Pull Request ${pr_num} to ${deploy_env} environment",
            mimeType: "text/html",
            from: "zabbix@weigandt-consulting.com",
            body: """<h4 style="font-family:tahoma;">The following <a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${pr_num}">GitHub Pull Request</a> has initiated deployment:</h4>
            <ul style="font-family:tahoma;">
            <li>PR name:        <b>${pr_title}</b>;</li>
            <li>From branch:    <b>${head_branch}</b>;</li>
            <li>To branch:      <b>${base_branch}</b>;</li>
            <li>By user:        <b>${pr_user}</b>;</li>
            <li>Jenkins build:  <b>${build_number}</b>;</li>
            <li>Hostname:       <b>${hostname}</li>
            <li>IP:             <b>${ip_addr}</li>
            </ul>
            <p style="font-family:tahoma;">Files scheduled for deployment can be found <a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${pr_num}/files">here</a>.</p>
            <p style="font-family:tahoma;">Your friendly neighborhood, Jenkins </p>""",
            to: "${email_addrs[0]}, ${email_addrs[0]}"
        )
    } 
    else if (type == "Failed") {
        /* if failed, then attaching jenkins console output */
        emailext (
            attachLog: true,
            subject: "Deployment of Pull Request #${pr_num} to ${deploy_env} environment FAILED",
            mimeType: 'text/html',
            from: "zabbix@weigandt-consulting.com",
            body: """<h4 style="font-family:tahoma;color:rgb(247,32,9)">FAILURE</h4/
            <ul style="font-family:tahoma;">
            <li>PR ref:         <b><a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${pr_num}">GitHub</a></b>;</li>
            <li>PR name:        <b>${pr_title}</b>;</li>
            <li>From branch:    <b>${head_branch}</b>;</li>
            <li>To branch:      <b>${base_branch}</b>;</li>
            <li>By user:        <b>${pr_user}</b>;</li>
            <li>Jenkins build:  <b>${build_number}</b>;</li>
            <li>Hostname:       <b>${hostname}</li>
            <li>IP:             <b>${ip_addr}</li>
            </ul>
            <p style="font-family:tahoma;">Your friendly neighborhood, Jenkins </p>""",
            to: "${email_addrs[0]}, ${email_addrs[0]}"
        )
    }
    else if (type == "Success") {
        emailext (
            subject: "Deployment of Pull Request #${pr_num} to ${deploy_env} environment SUCCEEDED",
            mimeType: 'text/html',
            from: "zabbix@weigandt-consulting.com",
            body: """<h4 style="font-family:tahoma;color:rgb(40,224,31)">SUCCESS</h4/
            <ul style="font-family:tahoma;">
              <li>PR ref:         <b><a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${pr_num}">GitHub</a></b>;</li>
              <li>PR name:        <b>${pr_title}</b>;</li>
              <li>From branch:    <b>${head_branch}</b>;</li>
              <li>To branch:      <b>${base_branch}</b>;</li>
              <li>By user:        <b>${pr_user}</b>;</li>
              <li>Jenkins build:  <b>${build_number}</b>;</li>
              <li>Hostname:       <b>${hostname}</li>
              <li>IP:             <b>${ip_addr}</li>
            </ul>
            <p style="font-family:tahoma;">Your friendly neighborhood, Jenkins </p>""",
            to: "${email_addrs[0]}, ${email_addrs[0]}"
        )
    }
}

return this

/*failure {
    emailext (
    attachLog: true, 
    subject: "Deployment of Pull Request #${params.PR_NUM} to ${env.DEPLOY_ENV} environment FAILED",
    mimeType: 'text/html',
    from: "zabbix@weigandt-consulting.com",
    body: """<h4 style="font-family:tahoma;color:rgb(247,32,9)">FAILURE</h4/
    <ul style="font-family:tahoma;">
        <li>PR ref:         <b><a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${params.PR_NUM}">GitHub</a></b>;</li>
        <li>PR name:        <b>${params.PR_TITLE}</b>;</li>
        <li>From branch:    <b>${params.HEAD_BRANCH}</b>;</li>
        <li>To branch:      <b>${params.BASE_BRANCH}</b>;</li>
        <li>By user:        <b>${params.PR_USER}</b>;</li>
        <li>Jenkins build:  <b>${env.BUILD_NUMBER}</b>;</li>
        <li>Hostname:       <b>${env.HOSTNAME}</li>
        <li>IP:       <b>${env.IP_ADDR}</li>
    </ul>
    <p style="font-family:tahoma;">Your friendly neighborhood, Jenkins </p>""",
    to: "${env.email_add1}, ${env.email_add2}"
    )
}

emailext (
      subject: "Starting deployment of Pull Request #${params.PR_NUM} to ${env.DEPLOY_ENV} environment",
      mimeType: "text/html",
      from: "zabbix@weigandt-consulting.com",
      body: """<h4 style="font-family:tahoma;">The following <a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${params.PR_NUM}">GitHub Pull Request</a> has initiated deployment:</h4>
      <ul style="font-family:tahoma;">
        <li>PR name:        <b>${params.PR_TITLE}</b>;</li>
        <li>From branch:    <b>${params.HEAD_BRANCH}</b>;</li>
        <li>To branch:      <b>${params.BASE_BRANCH}</b>;</li>
        <li>By user:        <b>${params.PR_USER}</b>;</li>
        <li>Jenkins build:  <b>${env.BUILD_NUMBER}</b>;</li>
        <li>Hostname:       <b>${env.HOSTNAME}</li>
        <li>IP:       <b>${env.IP_ADDR}</li>
      </ul>
      <p style="font-family:tahoma;">Files scheduled for deployment can be found <a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${params.PR_NUM}/files">here</a>.</p>
      <p style="font-family:tahoma;">Your friendly neighborhood, Jenkins </p>""",
      to: "${env.email_add1}, ${env.email_add2}"
)


success {
  emailext (
    subject: "Deployment of Pull Request #${params.PR_NUM} to ${env.DEPLOY_ENV} environment SUCCEEDED",
    mimeType: 'text/html',
    from: "zabbix@weigandt-consulting.com",
    body: """<h4 style="font-family:tahoma;color:rgb(40,224,31)">SUCCESS</h4/
    <ul style="font-family:tahoma;">
      <li>PR ref:         <b><a href="https://github.com/icanneverwin/webhook_jenkins_test/pull/${params.PR_NUM}">GitHub</a></b>;</li>
      <li>PR name:        <b>${params.PR_TITLE}</b>;</li>
      <li>From branch:    <b>${params.HEAD_BRANCH}</b>;</li>
      <li>To branch:      <b>${params.BASE_BRANCH}</b>;</li>
      <li>By user:        <b>${params.PR_USER}</b>;</li>
      <li>Jenkins build:  <b>${env.BUILD_NUMBER}</b>;</li>
      <li>Hostname:       <b>${env.HOSTNAME}</li>
      <li>IP:       <b>${env.IP_ADDR}</li>
    </ul>
    <p style="font-family:tahoma;">Your friendly neighborhood, Jenkins </p>""",
    to: "${env.email_add1}, ${env.email_add2}"
  )
}
*/
