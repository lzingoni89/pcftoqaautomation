using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;
using TAF.Core;

namespace SB10.BillingCenter
{
    public class ${pcf}Elements : IPageElements
    {
<#list pcf.widgets as widget>
        [FindsBy(How = How.Id, Using = "${widget.renderID}")]
        public IWebElement ${widget.widgetPCFID} { get; set; }

</#list>
    }

}