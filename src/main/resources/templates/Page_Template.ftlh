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
            get { try { return this.${pcfName}Elements.AccountSearchScreen.Displayed; } catch (NoSuchElementException) { return false; } }

        }

        public void SetTxtAccountNumber(string accountNumber) => this.CompleteField(this.AccountSearchScreenElements.TxtAccountNumber, accountNumber);
        public void SetTxtAccountName(string accountName) => this.CompleteField(this.AccountSearchScreenElements.TxtAccountName, accountName);
        public void SetTxtPolicyNumber(string policyNumber) => this.CompleteField(this.AccountSearchScreenElements.TxtPolicyNumber, policyNumber);
        public void SetCbAccountSegment(AccountSearchScreenElements.AccountSegmentOptions accountSegment) => this.SelectOption(this.AccountSearchScreenElements.CbAccountSegment, accountSegment.ToString(), SelectOptionBy.ByValue);
        public void SetCbDelinquencyStatus(AccountSearchScreenElements.DelinquencyStatusOptions delinquencyStatus) => this.SelectOption(this.AccountSearchScreenElements.CbDelinquencyStatus, delinquencyStatus.ToString(), SelectOptionBy.ByValue);
        public void SetCbAccountType(AccountSearchScreenElements.AccountTypeOptions accountType) => this.SelectOption(this.AccountSearchScreenElements.CbAccountType, accountType.ToString(), SelectOptionBy.ByValue);
        public void SetTxtOrganizationType(string organizationType) => this.CompleteField(this.AccountSearchScreenElements.TxtOrganizationType, organizationType);
        public void SetTxtFEIN(string fein) => this.CompleteField(this.AccountSearchScreenElements.TxtFEIN, fein);
        public void PressChkHasChargeHolds() => this.PressBtn(this.AccountSearchScreenElements.ChkHasChargeHolds);
        public void SetTxtCompanyName(string companyName) => this.CompleteField(this.AccountSearchScreenElements.TxtCompanyName, companyName);
        public void SetTxtFirstName(string firstCompany) => this.CompleteField(this.AccountSearchScreenElements.TxtFirstName, firstCompany);
        public void SetTxtLastName(string lastCompany) => this.CompleteField(this.AccountSearchScreenElements.TxtLastName, lastCompany);
        public void SetCbCountry(AccountSearchScreenElements.CountryOptions country) => this.SelectOption(this.AccountSearchScreenElements.CbCountry, country.ToString(), SelectOptionBy.ByValue);
        public void SetTxtPostalCode(string postalCode) => this.CompleteField(this.AccountSearchScreenElements.TxtPostalCode, postalCode);
        public void SetTxtCity(string city) => this.CompleteField(this.AccountSearchScreenElements.TxtCity, city);

        public IEnumerable<IWebElement> GridSearchResults()
        {
            return this.GridRows(this.AccountSearchScreenElements.GridSearchResult);
        }

        public void PressBtnSearch()
        {
            this.PressBtn(this.AccountSearchScreenElements.BtnSearch);
        }

        public void PressBtnReset()
        {
            this.PressBtn(this.AccountSearchScreenElements.BtnReset);
        }

    }

}