module.exports = function (config) {
    config.set({
        singleRun: true,
        browsers: ['ChromeHeadlessNoSandbox'],
        files: ['target/karma-test.js'].concat(require('./target/files.json')),
        preprocessors: {
            'target/files/coverage.!(*_test).js': ['coverage']
        },
        frameworks: ['cljs-test'],
        plugins: [
            'karma-cljs-test',
            'karma-chrome-launcher',
            'karma-coverage'
        ],
        colors: true,
        logLevel: config.LOG_INFO,
        client: {
            args: ['shadow.test.karma.init']
        },

        customLaunchers: {
            ChromeHeadlessNoSandbox: {
                base: 'ChromeHeadless',
                flags: ['--no-sandbox']
            }
        },

        reporters: ['coverage'],

        coverageReporter: {
            reporters: [
                {type: 'text'},
                {type: 'text-summary'},
            ]
        }
    })
}
