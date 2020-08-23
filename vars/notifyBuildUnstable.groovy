def call() {
    emailext to: 'notifications@logging.apache.org',
        from: 'Mr. Jenkins <jenkins@ci-builds.apache.org>',
        subject: "[CI][UNSTABLE] ${env.JOB_NAME}#${env.BUILD_NUMBER} has test failures",
        body: '${SCRIPT, template="text.jelly"}'
}
