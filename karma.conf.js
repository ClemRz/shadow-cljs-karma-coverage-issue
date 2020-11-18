module.exports = function (config) {
    config.set({
        singleRun: true,
        browsers: ['ChromeHeadlessNoSandbox'],
        files: ['target/karma-test.js'].concat(require('./target/files.json')),
        preprocessors: {
            'target/cljs-runtime/coverage.!(*test*).js': ['coverage']
        },
        frameworks: ['cljs-test'],
        plugins: [
            'karma-cljs-test',
            'karma-chrome-launcher',
            'karma-remap-coverage',
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

        reporters: ['coverage', 'remap-coverage'],

        coverageReporter: {
            reporters: [
                {type: 'in-memory'}
            ]
        },

        remapOptions: { basePath: './src/main' },

        remapCoverageReporter: {
            'text': null,
            'text-summary': null
        }
    })
}
