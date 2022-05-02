# CA1 Teardown file
# Name: Eoghan Fletcher

# If $VpcId is not populated don't execute the rest of the script
if ( $null -eq $VpcId -or "" -eq $VpcId ) {
	Write-Host '$VpcId is empty, canceling system_teardown.ps1 execution'  -Foreground Red
	Exit;
}
else { Write-Host '$VpcId found, executing script' -Foreground Green }


# EC2

    # Terminate instance
    Write-Host "Terminate instance"
aws ec2 terminate-instances --instance-id $Instance

    # Wait for instance to terminate before attempting to detach and delete required resources
    Write-Host 'Instance is terminating'
aws ec2 wait instance-terminated --instance-ids $Instance

    # Delete Subnet
    Write-Host 'Delete Subnet'
aws ec2 delete-subnet --subnet-id $SubnetId

    # Detach and delete Internet Gateway
    Write-Host 'Detach and delete Internet Gateway'
aws ec2 detach-internet-gateway --internet-gateway-id $IGWId --vpc-id $VpcId
aws ec2 delete-internet-gateway --internet-gateway-id $IGWId

    # Delete Security Group
    Write-Host 'Delete Security Group'
aws ec2 delete-security-group --group-id $SGId

    # Delete VPC
    Write-Host 'Delete VPC'
aws ec2 delete-vpc --vpc-id $VpcId
Write-Host ''

# Remove global variables
Write-Host "Terminated instance, deleted Subnet, detached and deleted Internet Gateway, deleted Security Group, deleted VPC"
Write-Host 'The following PowerShell variables now contain empty strings: $VpcId, $SubnetId, $RTId, $SGId, $IGWid, $Instance, $PublicIpAddress'

$global:VpcId=""
$global:SubnetId=""
$global:RTId=""
$global:SGId=""
$global:IGWId=""
$global:Instance=""
$global:PublicIpAddress=""

Write-Host ''
Write-Host 'System Teardown Complete' -Foreground Green