using System.Collections.Generic;
using System.Threading;
using OpenQA.Selenium;
using static TAF.Core.PageBaseEnums;

namespace ${companyNamespace}.${moduleNamespace}
{
    public class ${pcfName}Page : ${moduleNamespace}PageBase
    {
        private ${pcfName}Elements ${pcfName}Elements => (${pcfName}Elements)this.Elements;

        public bool IsDisplayed
        {
            get { try { return this.${pcfName}Elements.${pcfName}Screen.Displayed; } catch (NoSuchElementException) { return false; } }
        }

<#list widgets as widget>
<#switch widget.widgetType.prefix.prefix>
    <#case "Txt">
        public void Set${widget.widgetType.prefix.prefix}${widget.widgetPCFID}(string ${widget.widgetPCFID}) => this.CompleteField(this.${pcfName}Elements.${widget.widgetType.prefix.prefix}${widget.widgetPCFID}, ${widget.widgetPCFID});

        <#break>
    <#case "Cb">
        public void Set${widget.widgetType.prefix.prefix}${widget.widgetPCFID}(${widget.enumRef}Enum selectedOption) => this.SelectOption(this.${pcfName}Elements.${widget.widgetType.prefix.prefix}${widget.widgetPCFID}, selectedOption.ToString(), SelectOptionBy.ByValue);

        <#break>
    <#case "Chk">
        public void PressChk${widget.widgetPCFID}() => this.PressBtn(this.${pcfName}Elements.${widget.widgetType.prefix.prefix}${widget.widgetPCFID});

        <#break>
    <#case "Grid">
        public IEnumerable<IWebElement> Grid${widget.widgetPCFID}Result()
        {
            return this.GridRows(this.${pcfName}Elements.${widget.widgetType.prefix.prefix}${widget.widgetPCFID});
        }

        <#break>
    <#case "Tlt">
        <#break>
    <#case "Lnk">
        <#break>
    <#case "Msg">
        <#break>
    <#case "Tab">
        <#break>
    <#case "Loc">
        public void GoTo${widget.widgetPCFID}()
        {
            var expandElement = this.${pcfName}Elements.${widget.parentVarName}.FindElement(By.CssSelector("div[class*='gw-icon--expand']"));
            this.MoveToElement(expandElement);
            this.PressBtn(this.${pcfName}Elements.${widget.widgetType.prefix.prefix}${widget.widgetPCFID});

        }

        <#break>
    <#case "Btn">
        public void PressBtn${widget.widgetPCFID}() => this.PressBtn(this.${pcfName}Elements.${widget.widgetType.prefix.prefix}${widget.widgetPCFID});

        <#break>
    <#default>
        ERROR ${widget.widgetPCFID}

</#switch>
</#list>

    }

}