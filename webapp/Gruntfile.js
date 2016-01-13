// Generated on 2015-02-16 using generator-angular 0.11.1
'use strict';

// # Globbing
// for performance reasons we're only matching one level down:
// 'test/spec/{,*/}*.js'
// use this if you want to recursively match all subfolders:
// 'test/spec/**/*.js'
var proxySnippet = require('grunt-connect-proxy/lib/utils').proxyRequest;

module.exports = function (grunt) {

	// Load grunt tasks automatically
	require('load-grunt-tasks')(grunt);

	// Time how long tasks take. Can help when optimizing build times
	require('time-grunt')(grunt);

	var serveStatic = require('serve-static');

	// Configurable paths for the application
	var appConfig = {
		app: require('./bower.json').appPath || 'app',
		dist: 'dist'
	};

	// Define the configuration for all the tasks
	grunt.initConfig({

		// Project settings
		yama: appConfig,

		// Watches files for changes and runs tasks based on the changed files
		watch: {
			bower: {
				files: ['bower.json'],
				tasks: ['newer:jsonlint:bower', 'wiredep']
			},
			jsChanged: {
				files: ['<%= yama.app %>/**/*.js'],
				tasks: ['newer:jshint:all'],
				options: {
					event: 'changed',
					livereload: '<%= connect.options.livereload %>'
				}
			},
			jsAddedOrDeleted: {
				files: ['<%= yama.app %>/**/*.js'],
				tasks: ['injector:js'],
				options: {
					event: [ 'added', 'deleted' ],
					livereload: '<%= connect.options.livereload %>'
				}
			},
			jsTest: {
				files: ['<%= yama.app %>/**/*.spec.js'],
				tasks: ['newer:jshint:test', 'karma']
			},
			json: {
				files: ['<%= yama.app %>/i18n/*.json'],
				tasks: ['newer:jsonlint:app'],
				options: {
					livereload: '<%= connect.options.livereload %>'
				}
			},
			styles: {
				files: ['<%= yama.app %>/**/*.css'],
				tasks: [ 'injector:css', 'newer:copy:styles', 'autoprefixer']
			},
			gruntfile: {
				files: ['Gruntfile.js']
			},
			livereload: {
				files: [
					'<%= yama.app %>/**/*.html',
					'.tmp/**/*.css',
					'<%= yama.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
				],
				options: {
					livereload: '<%= connect.options.livereload %>'
				}
			}
		},

		// The actual grunt server settings
		connect: {
			options: {
				port: 8081,
				// Change this to '0.0.0.0' to access the server from outside.
				hostname: '0.0.0.0',
				livereload: 35729
			},
			proxies: [
			{
				context: '/',
				host: 'localhost',
				port: 8080
			}
			],
			livereload: {
				options: {
					open: true,
					base: [
						'.tmp',
						appConfig.app
					],
					middleware: function (connect) {
						// Setup the proxy
						var middlewares = [
							serveStatic('.tmp'),
							connect().use( '/bower_components', serveStatic('./bower_components')),
							serveStatic(appConfig.app),
							proxySnippet
						];

						return middlewares;
					}
				}
			},
			test: {
				options: {
					port: 9002,
					base: [
						'.tmp',
						'test',
						appConfig.app
					]
					// ,
					// middleware: function (connect) {
					// 	return [
					// 		connect.static('.tmp'),
					// 		connect.static('test'),
					// 		connect().use(
					// 			'/bower_components',
					// 			connect.static('./bower_components')
					// 		),
					// 		connect.static(appConfig.app)
					// 	];
					// }
				}
			},
			dist: {
				options: {
					open: true,
					base: '<%= yama.dist %>'
				}
			}
		},

		// Make sure code styles are up to par and there are no obvious mistakes
		jshint: {
			options: {
				jshintrc: '.jshintrc',
				reporter: require('jshint-stylish')
			},
			config: {
				src: 'Gruntfile.js'
			},
			all: {
				src: [
					'Gruntfile.js',
					'<%= yama.app %>/**/*.js'
				]
			},
			test: {
				options: {
					jshintrc: 'test/.jshintrc'
				},
				src: ['<%= yama.app %>/**/*.spec.js']
			}
		},

		jsonlint: {
			bower: {
				src: [ 'bower.json' ]
			},
			app: {
				src: [ '<%= yama.app %>/i18n/*.json' ]
			}
		},

		'json-minify': {
			dist: {
				files: '<%= yama.dist %>/i18n/*.json'
			}
		},

		// Empties folders to start fresh
		clean: {
			dist: {
				files: [{
					dot: true,
					src: [
						'.tmp',
						'<%= yama.dist %>/{,*/}*',
						'!<%= yama.dist %>/.git{,*/}*'
					]
				}]
			},
			server: '.tmp'
		},

		// Add vendor prefixed styles
		autoprefixer: {
			options: {
				browsers: ['last 2 version']
			},
			server: {
				options: {
					map: true,
				},
				files: [{
					expand: true,
					cwd: '.tmp/styles/',
					src: '**/*.css',
					dest: '.tmp/styles/'
				}]
			}
		},

		// Automatically inject Bower components into the app
		wiredep: {
			app: {
				src: ['<%= yama.app %>/**/*.html'],
				ignorePath:  /\.\.\//
			},
			test: {
				devDependencies: true,
				src: '<%= karma.unit.configFile %>',
				ignorePath:	/\.\.\//,
				fileTypes:{
					js: {
						block: /(([\s\t]*)\/{2}\s*?bower:\s*?(\S*))(\n|\r|.)*?(\/{2}\s*endbower)/gi,
							detect: {
								js: /'(.*\.js)'/gi
							},
							replace: {
								js: '\'{{filePath}}\','
							}
						}
					}
			}
		},

		// Automatically inject scripts and styles into the app
		injector: {
			options: {
				ignorePath: '<%= yama.app %>',
				addRootSlash: false
			},
			js: {
				files: {
					'<%= yama.app %>/index.html': [
						'<%= yama.app %>/**/*.js',
						'!<%= yama.app %>/app.js',
						'!<%= yama.app %>/components/oauth2/oauth2.js',
						'!<%= yama.app %>/frontend/register/*.js'
					],
				}
			},
			css: {
				files: {
					'<%= yama.app %>/index.html': [
						'<%= yama.app %>/**/*.css'
					],
				}
			}
		},

		// Renames files for browser caching purposes
		filerev: {
			dist: {
				src: [
					'<%= yama.dist %>/scripts/{,*/}*.js',
					'<%= yama.dist %>/styles/{,*/}*.css',
					'<%= yama.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
					'!<%= yama.dist %>/images/default_profile.gif',
					'<%= yama.dist %>/styles/fonts/*'
				]
			}
		},

		// Reads HTML for usemin blocks to enable smart builds that automatically
		// concat, minify and revision files. Creates configurations in memory so
		// additional tasks can operate on them
		useminPrepare: {
			html: '<%= yama.app %>/index.html',
			options: {
				dest: '<%= yama.dist %>',
				flow: {
					html: {
						steps: {
							js: ['concat', 'uglifyjs'],
							css: ['cssmin']
						},
						post: {}
					}
				}
			}
		},

		// Performs rewrites based on filerev and the useminPrepare configuration
		usemin: {
			html: ['<%= yama.dist %>/**/*.html'],
			css: ['<%= yama.dist %>/**/*.css'],
			options: {
				assetsDirs: [
					'<%= yama.dist %>',
					'<%= yama.dist %>/images',
					'<%= yama.dist %>/styles'
				]
			}
		},

		// The following *-min tasks will produce minified files in the dist folder
		// By default, your `index.html`'s <!-- Usemin block --> will take care of
		// minification. These next options are pre-configured if you do not wish
		// to use the Usemin blocks.
		// cssmin: {
		//	 dist: {
		//		 files: {
		//			 '<%= yama.dist %>/styles/main.css': [
		//				 '.tmp/styles/{,*/}*.css'
		//			 ]
		//		 }
		//	 }
		// },
		// uglify: {
		//	 dist: {
		//		 files: {
		//			 '<%= yama.dist %>/scripts/scripts.js': [
		//				 '<%= yama.dist %>/scripts/scripts.js'
		//			 ]
		//		 }
		//	 }
		// },
		// concat: {
		//	 dist: {}
		// },

		imagemin: {
			dist: {
				files: [{
					expand: true,
					cwd: '<%= yama.app %>/images',
					src: '{,*/}*.{png,jpg,jpeg,gif}',
					dest: '<%= yama.dist %>/images'
				}]
			}
		},

		svgmin: {
			dist: {
				files: [{
					expand: true,
					cwd: '<%= yama.app %>/images',
					src: '{,*/}*.svg',
					dest: '<%= yama.dist %>/images'
				}]
			}
		},

		htmlmin: {
			dist: {
				options: {
					collapseWhitespace: true,
					conservativeCollapse: true,
					collapseBooleanAttributes: true,
					removeCommentsFromCDATA: true,
					removeOptionalTags: true
				},
				files: [{
					expand: true,
					cwd: '<%= yama.dist %>',
					src: ['*.html', '**/*.html'],
					dest: '<%= yama.dist %>'
				}]
			}
		},

		// ng-annotate tries to make the code safe for minification automatically
		// by using the Angular long form for dependency injection.
		ngAnnotate: {
			dist: {
				files: [{
					expand: true,
					cwd: '.tmp/concat/scripts',
					src: '*.js',
					dest: '.tmp/concat/scripts'
				}]
			}
		},

		// Copies remaining files to places other tasks can use
		copy: {
			dist: {
				files: [{
					expand: true,
					dot: true,
					cwd: '<%= yama.app %>',
					dest: '<%= yama.dist %>',
					src: [
						'*.{ico,png,txt}',
						'.htaccess',
						'*.html',
						'**/*.html',
						'images/{,*/}*.{webp}',
						'styles/fonts/{,*/}*.*',
						'i18n/*.json'
					]
				}, {
					expand: true,
					cwd: '.tmp/images',
					dest: '<%= yama.dist %>/images',
					src: ['generated/*']
				}, {
					expand: true,
					cwd: 'bower_components/bootstrap/dist',
					src: 'fonts/*',
					dest: '<%= yama.dist %>'
				}, {
					expand: true,
					cwd: 'bower_components/font-awesome',
					src: 'fonts/*',
					dest: '<%= yama.dist %>'
				}]
			},
			styles: {
				expand: true,
				cwd: '<%= yama.app %>',
				dest: '.tmp/styles/',
				src: '**/*.css'
			}
		},

		// Run some tasks in parallel to speed up the build process
		concurrent: {
			dev: [
				'copy:styles'
			],
			test: [
				'copy:styles'
			],
			dist: [
				'copy:styles',
				'imagemin',
				'svgmin'
			]
		},

		// Test settings
		karma: {
			unit: {
				configFile: 'test/karma.conf.js',
				singleRun: true
			}
		}
	});


	grunt.registerTask('serve', 'Compile then start a connect web server', function (target) {
		if (target === 'dist') {
			return grunt.task.run(['build', 'connect:dist:keepalive']);
		}

		grunt.task.run([
			'clean:server',
			'concurrent:dev',
			'wiredep',
			'injector',
			'jshint:config',
			'jsonlint',
			'autoprefixer',
			'configureProxies',
			'connect:livereload',
			'watch'
		]);
	});

	grunt.registerTask('server', 'DEPRECATED TASK. Use the "serve" task instead', function (target) {
		grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
		grunt.task.run(['serve:' + target]);
	});

	grunt.registerTask('test', [
		'clean:server',
		'wiredep',
		'injector',
		'concurrent:test',
		'autoprefixer',
		'connect:test',
		'karma'
	]);

	grunt.registerTask('build', [
		'clean:dist',
		'wiredep',
		'injector',
		'useminPrepare',
		'concurrent:dist',
		'autoprefixer',
		'concat',
		'ngAnnotate',
		'copy:dist',
		'cssmin',
		'uglify',
		'json-minify:dist',
		'filerev',
		'usemin',
		'htmlmin'
	]);

	grunt.registerTask('default', [
		'newer:jshint',
		'test',
		'build'
	]);
};
