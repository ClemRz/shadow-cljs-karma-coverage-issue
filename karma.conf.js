module.exports = function (config) {
    config.set({
        singleRun: true,
        browsers: ['ChromeHeadlessNoSandbox'],
        basePath: 'target',
        files: ['karma-*.js'],
        preprocessors: {
            'karma-src.js': ['coverage']
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
