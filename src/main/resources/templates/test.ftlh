using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;
using TAF.Core;

namespace SB10.BillingCenter
{
    public class ${pcf}Elements : IPageElements
    {
<#list container.children as widget>
        [FindsBy(How = How.Id, Using = "${widget.renderID}")]
        public IWebElement ${widget.widgetID} { get; set; }

</#list>
    }

}