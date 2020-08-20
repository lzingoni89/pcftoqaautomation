using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;
using TAF.Core;

namespace ${companyNamespace}.${moduleNamespace}
{
    public class ${pcfName}Elements : IPageElements
    {
        [FindsBy(How = How.Id, Using = "${pcfName}${locationGroup}")]
        public IWebElement ${pcfName}Screen { get; set; }

<#list widgets as widget>
        [FindsBy(How = How.${widget.widgetType.findBy.how}, Using = "${widget.renderID}")]
        public IWebElement ${widget.widgetType.prefix.prefix}${widget.widgetPCFID} { get; set; }

</#list>
    }

}