[#ftl]
[#--
* Copyright (c) 2005-2010 Grameen Foundation USA
*  All rights reserved.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
*  implied. See the License for the specific language governing
*  permissions and limitations under the License.
*
*  See also http://www.apache.org/licenses/LICENSE-2.0.html for an
*  explanation of the license and how it is applied.
--]
[#import "spring.ftl" as spring]
[#import "newblueprintmacros.ftl" as mifos]
[@mifos.header "title" /]
[@mifos.topNavigationNoSecurity currentTab="Admin" /]
<div class="colmask leftmenu">
    <div class="colleft">
        <div class="col1wrap">
            <div class="col1">
            <div class="main_content">

                <span id="page.id" title="view_questions"/></span>

                [#assign breadcrumb = {"admin":"AdminAction.do?method=load", "questionnaire.view.questions":""}/]
                [@mifos.crumbpairs breadcrumb/]

               <div class="content_panel">
                    <h1>
                        [@spring.message "questionnaire.view.questions"/]
                    </h1>
                    <p>
                        [@spring.message "questionnaire.create.question.prompt"/]
                        <a href="createQuestion.ftl">[@spring.message "questionnaire.create.question.link"/]</a>
                    </p>
                    <ul class="questions">
                        [#list questions as question]
                        <li>
                            <a href="viewQuestionDetail.ftl?questionId=${question.id}" id="questionId_${question.id}">${question.title}</a>
                        </li>
                        [/#list]
                    </ul>
                </div>
            </div>
        </div>
        </div>
        <div class="col2">
            <div class="side_bar">
                [#include "newadminLeftPane.ftl" /]
            </div>
        </div>
    </div>
</div>
[@mifos.footer/]