#!/usr/bin/env bash
while true; do
  echo "                 ############## input command code #################"
  echo "                 #          [1] Git Push                           #"
  echo "                 #          [2] Git Push And Merge to Master       #"
  echo "                 #          [other] exit                           #"
  echo "                 ###################################################"

  read which

  case $which in
  1)
    curBranch=$(git symbolic-ref --short -q HEAD)
    gitPush $curBranch
    ;;
  2)
    curBranch=$(git symbolic-ref --short -q HEAD)
    gitPush $curBranch
    echo "git checkout master"
    echo $(git checkout master)
    echo "git merge $branchName"
    echo $(git merge $branchName)
    echo "git push origin master"
    echo $(git push origin master)
    echo "git checkout $branchName"
    echo $(git checkout $branchName)
    ;;
  *)
    echo "88"
    break
    ;;
  esac
done

function gitPush() {
  curBranch=$1
  echo "curBranch = $curBranch"
  echo "git add -A"
  echo $(git add -A)
  date=$(date "+%m/%d")
  echo "git commit -m \"see $date log\""
  echo $(git commit -m "see $date log")
  echo "git push origin $curBranch"
  echo $(git push origin $curBranch)
}
