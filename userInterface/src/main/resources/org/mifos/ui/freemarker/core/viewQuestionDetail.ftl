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
[#import "blueprintmacros.ftl" as mifos]
[@mifos.header "title" /]
[@mifos.topNavigationNoSecurity currentTab="Admin" /]
<div class="sidebar ht950">
    [#include "adminLeftPane.ftl" /]
</div>
<div class="content leftMargin180">
    <span id="page.id" title="view_question_details"/></span>

    <div class="fontnormal">
        [#if error_message_code??]
            [@spring.message error_message_code/]
        [#else]
            [#assign breadcrumb = {"admin":"AdminAction.do?method=load", "questionnaire.view.questions":"viewQuestions.ftl",Request.questionDetail.title:""}/]
            [@mifos.crumbpairs breadcrumb/]
            <div class="marginLeft30">
                <div class="orangeheading marginTop15">
                    ${Request.questionDetail.title}
                </div>
                <div class="marginTop15">
                    [@spring.message "questionnaire.question"/]: ${Request.questionDetail.title}<br/>
                    [@spring.message "questionnaire.answer.type"/]: ${Request.questionDetail.type}
                </div>
            </div>
        [/#if]
    </div>
</div>
[@mifos.footer/]