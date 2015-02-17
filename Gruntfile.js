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
			build_jar: 'mvn clean package',
			npm_install: {
				cwd: 'webapp',
				cmd: 'npm install'
			},
			bower_install: {
				cwd: 'webapp',
				cmd: 'bower install'
			}
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
			webapi: '<%= yama.apidist %>'
		},
		copy: {
			login: {
				files: [{
					expand: true,
					dot: true,
					cwd: '<%= yama.webdist %>',
					dest: '<%= yama.apidist %>',
					src: [
						'*.{ico,png,txt}',
						'login.html',
						'images/{,*/}*.*',
						'styles/{,*/}*.css',
						'fonts/{,*/}*.*'
					]
				}]
			},
			dist: {
				expand: true,
				cwd: '<%= yama.webdist %>',
				dest: '<%= yama.apidist %>',
				src: '**'
			}
		}
	});

	grunt.registerTask('init', [
		'exec:npm_install',
		'exec:bower_install',
		'subgrunt:build_client',
		'copy:login'
	]);

	grunt.registerTask('build', [
		'clean:webapi',
		'exec:npm_install',
		'exec:bower_install',
		'subgrunt:build_client',
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
