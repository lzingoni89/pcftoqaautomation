using OpenQA.Selenium;
using OpenQA.Selenium.Support.PageObjects;
using TAF.Core;

namespace SB10.BillingCenter
{
    public class TabBarElements : IPageElements
    {
        [FindsBy(How = How.Id, Using = "TabBar-LanguageTabBarLink")]
        public IWebElement LanguageTabBarLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-HelpTabBarLink")]
        public IWebElement HelpTabBarLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-AboutTabBarLink")]
        public IWebElement AboutTabBarLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-PrefsTabBarLink")]
        public IWebElement PrefsTabBarLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-OpenPreferencesTabBarLink")]
        public IWebElement OpenPreferencesTabBarLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-ReloadPCFTabBarLink")]
        public IWebElement ReloadPCFTabBarLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-LogoutTabBarLink")]
        public IWebElement LogoutTabBarLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-ContactsTab")]
        public IWebElement ContactsTab { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-ProfilerHiddenLink")]
        public IWebElement ProfilerHiddenLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-AdminTab")]
        public IWebElement AdminTab { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-InternalToolsHiddenLink")]
        public IWebElement InternalToolsHiddenLink { get; set; }

        [FindsBy(How = How.Id, Using = "TabBar-UnsavedWorkTabBarLink")]
        public IWebElement UnsavedWorkTabBarLink { get; set; }

    }

}