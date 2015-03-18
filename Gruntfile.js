'use strict';

// # Globbing
module.exports = function (grunt) {
	// Load grunt tasks automatically
	require('load-grunt-tasks')(grunt);

	// Time how long tasks take. Can help when optimizing build times
	require('time-grunt')(grunt);

	var appConfig = {
		webdist: 'webapp/dist',
		apidist: 'webapi/src/main/resources/public'
	};

	grunt.initConfig({
		yama: appConfig,

		exec: {
			run_server: 'mvn test -Pwebapi',
			build_jar: 'mvn clean package'
		},
		subgrunt: {
			run_client: {
				'webapp': 'serve'
			},
			build_client: {
				'webapp': 'build'
			}
		},
		clean: {
			webapi_conf: 'webapi/src/main/resources/application.yml',
			webapi: '<%= yama.apidist %>'
		},
		copy: {
			webapi_conf: {
				dest: 'webapi/src/main/resources/application.yml',
				src: 'webapi/src/main/resources/config/yama-prod.yml'
			},
			dist: {
				expand: true,
				cwd: '<%= yama.webdist %>',
				dest: '<%= yama.apidist %>',
				src: '**'
			}
		},
		auto_install: {
			webapp: {
				options: {
					cwd: 'webapp',
					bower: '--force-latest'
				}
			}
		}
	});

	grunt.registerTask('init', [
		'clean:webapi',
		'auto_install:webapp',
		'subgrunt:build_client',
		'copy:dist'
	]);

	grunt.registerTask('build', [
		'clean:webapi_conf',
		'clean:webapi',
		'auto_install:webapp',
		'subgrunt:build_client',
		'copy:webapi_conf',
		'copy:dist',
		'exec:build_jar'
	]);

	grunt.registerTask('server', [
		'exec:run_server'
	]);

	grunt.registerTask('client', [
		'subgrunt:run_client'
	]);
};
