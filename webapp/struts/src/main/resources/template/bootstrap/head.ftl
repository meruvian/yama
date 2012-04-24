<#--
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->

<#if parameters.compressed?default(true)>
  <#assign jsFile="bootstrap.min.js">
  <#assign cssFile="bootstrap.min.css">
  <#assign cssResponsive="bootstrap-responsive.min.css">
  <#assign validationFile="validation.min.js">
<#else>
  <#assign jsFile="bootstrap.js">
  <#assign cssFile="bootstrap.css">
  <#assign cssResponsive="bootstrap-responsive.css">
  <#assign validationFile="validation.js">
</#if>
<#assign cssAdditional="docs.css">

	<script type="text/javascript" src="${base}/static/bootstrap/js/${jsFile}"></script>
    <script type="text/javascript" src="${base}/static/bootstrap/js/${validationFile}"></script>
    <script type="text/javascript" src="${base}/static/bootstrap/js/bootbox.min.js"></script>
   	<link id="bootstrap_styles" rel="stylesheet" href="${base}/static/bootstrap/css/${cssFile}" type="text/css"/>
	<link id="bootstrap_responsive_styles" rel="stylesheet" href="${base}/static/bootstrap/css/${cssResponsive}" type="text/css"/>
	<link id="bootstrap_responsive_styles" rel="stylesheet" href="${base}/static/bootstrap/css/${cssAdditional}" type="text/css"/>