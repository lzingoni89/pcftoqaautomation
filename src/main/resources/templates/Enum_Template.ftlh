namespace ${enum.companyNamespace}.${enum.moduleNamespace}
{
    public enum ${enum.enumName}Enum
    {
<#list enum.values as value>
        ${value},
</#list>
    }
}