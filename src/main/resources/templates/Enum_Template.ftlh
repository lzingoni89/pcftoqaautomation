namespace ${companyNamespace}.${moduleNamespace}
{
    public enum ${enumName}Enum
    {
<#list values as value>
        ${value},
</#list>
    }
}