from jinja2 import Environment, FileSystemLoader, select_autoescape
import shutil
import os
from pathlib import Path
import re

loader = FileSystemLoader(searchpath="quickstart")
env = Environment(loader=loader)

# mapping of template variables to their values
templateVarMap = {}

BASE_PATH = "./base"
    
MIN_SDK = "24"
TARGET_SDK = "28"
TEMPLATE_RE = "\{\{\s*.*\s*\}\}"

# renders templated project files and directories
# 
# High level steps: (may not translate 1:1 with code)
# 1. create the project directory
# 2. copy base template files to newly created directory
# 3. render any files that require templating
# 4. rename any files/directories that depend on user input
#
def createProject(appName, package, path):
    # initialize template variables and their values
    templateVarMap["app_name"] = appName
    templateVarMap["app_classname"] = f"{appName}App"
    templateVarMap["pkg_name"] = package
    templateVarMap["min_sdk_ver"] = MIN_SDK
    templateVarMap["target_sdk_ver"] = TARGET_SDK

    # copy base files to new project path
    newPath = f"{path}/{appName}"   
    shutil.copytree(src=BASE_PATH, dst=newPath)

    renderFilesFromTemplates(appName, package, newPath)
    fillInNames(appName, package, newPath)

# renames files and directories that depend on user spec
# Ex. packages will need to be renamed per user requirements
def fillInNames(appName, package, path):
    TEMPLATE_PKG = "package_name"
    srcPath = f"{path}/app/src"

    srcDirs = ["androidTest", "main", "test"]
    for srcDir in srcDirs:
        oldPkgName = f"{srcPath}/{srcDir}/java/com/{TEMPLATE_PKG}"
        newPkgName = f"{srcPath}/{srcDir}/java/com/{package}"
        os.rename(src=oldPkgName, dst=newPkgName)

# runs through all template files and renders them
def renderFilesFromTemplates(appName, package, path):
    templateLoader = FileSystemLoader(searchpath=BASE_PATH)
    templateEnv = Environment(loader=templateLoader)
    renameRe = re.compile(TEMPLATE_RE)

    pkg_name = f"com.{package}"
    app_name = appName
    app_classname = f"{appName}App"

    for file in Path(path).glob("**/*.j2"):
        templatePath = file.__str__()[len(f"{path}/"):]
        newFilePath = file.__str__()

        print(templatePath)

        template = templateEnv.get_template(templatePath)
        rendered = template.render(
            pkg_name=pkg_name, 
            app_name=app_name, 
            app_classname=app_classname, 
            min_sdk_ver=MIN_SDK, 
            target_sdk_ver=TARGET_SDK
          )

        # check if file/dir name needs to be replaced
        templateFileName = os.path.basename(newFilePath)
        # remove template file from new project directory
        os.remove(newFilePath)

        if (renameRe.match(templateFileName)):
            # get template variable and retrieve its value
            templateVar = re.sub(".*{{|}}.*|\s*", "", templateFileName)
            print(templateVar)
            templateVarVal = templateVarMap[templateVar]

            # sub the value of the template variable in
            newFilePath = re.sub(TEMPLATE_RE, templateVarVal, newFilePath)
            print(newFilePath)

        # write rendered output to file, removed j2 extension
        with open(newFilePath[:-3], "w+") as fh:
            fh.write(rendered)

if (__name__ == "__main__"):
    createProject(appName="TestProject", package="testpackage", path="..")