def call() {
    emailext to: 'notifications@logging.apache.org',
        from: 'Mr. Jenkins <jenkins@ci-builds.apache.org>',
        subject: "[CI][SUCCESS] ${env.JOB_NAME}#${env.BUILD_NUMBER} back to normal",
        mimeType: 'text/html',
        body: '${JELLY_SCRIPT,template="html_gmail"}'
}
