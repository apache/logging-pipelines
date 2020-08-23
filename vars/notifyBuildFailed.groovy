def call() {
    emailext to: 'notifications@logging.apache.org',
        from: 'Mr. Jenkins <jenkins@ci-builds.apache.org>',
        subject: "[CI][FAILURE] ${env.JOB_NAME}#${env.BUILD_NUMBER} has potential issues",
        mimeType: 'text/html',
        body: '${JELLY_SCRIPT,template="html_gmail"}'
}
